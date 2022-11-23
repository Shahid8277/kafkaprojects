package com.technoelevate.kafka.producer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

import com.technoelevate.kafka.producer.dto.TopicDescDto;
import com.technoelevate.kafka.producer.dto.TopicDetailsDto;
import com.technoelevate.kafka.producer.entity.Topic;

@Service
public class TopicCreationServiceImpl implements TopicCreationService {

	@Value("${spring.kafka.producer.bootstrap-servers: localhost:9092,localhost:9093,localhost:9094}")
	private String bootstrapServers;

	@Override
	public String crateTopic(Topic topic) {
		AdminClient adminClient = getAdminClient();
		NewTopic build = TopicBuilder.name(topic.getTopicName()).partitions(topic.getPartitions())
				.replicas(topic.getReplicationFactor()).build();
		List<NewTopic> topics = List.of(build);
		adminClient.createTopics(topics);
		return "The topic "+topic.getTopicName()+" has been created";
	}

	@Override
	public String updateTopic(Topic topic) {
		AdminClient adminClient = getAdminClient();
		ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
		listTopicsOptions.listInternal(true);
		try {
			Set<String> topics = adminClient.listTopics(listTopicsOptions).names().get();
			Map<String, NewPartitions> newPartitions = topics.stream().filter(topi -> topic.getTopicName().equals(topi))
					.collect(Collectors.toMap(k -> k, v -> NewPartitions.increaseTo(topic.getPartitions())));
			adminClient.createPartitions(newPartitions);
		} catch (InterruptedException | ExecutionException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		return "The topic "+topic.getTopicName()+" has been updated";
	}

	@Override
	public List<TopicDetailsDto> getAllTopicWithDesc() {
		return getTopicDetailsDto(null);
	}

	@Override
	public TopicDetailsDto getTopicWithDesc(String topicName) {
		return getTopicDetailsDto(topicName).get(0);
	}

	private List<TopicDetailsDto> getTopicDetailsDto(String topicName) {
		List<TopicDetailsDto> topicDetails = new ArrayList<>();
		try (AdminClient adminClient = getAdminClient();) {
			ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
			listTopicsOptions.listInternal(true);

			Set<Entry<String, TopicDescription>> describeTopics = adminClient.describeTopics(
					topicName == null ? adminClient.listTopics(listTopicsOptions).names().get() : List.of(topicName))
					.allTopicNames().get().entrySet();
			for (Map.Entry<String, TopicDescription> topicDescription : describeTopics) {
				TopicDescription description = topicDescription.getValue();
				TopicDetailsDto topic = TopicDetailsDto.builder().topicName(description.name())
						.topicId(description.topicId().toString()).partitionCount(description.partitions().size())
						.build();
				ArrayList<TopicDescDto> descDtos = new ArrayList<>();
				description.partitions().forEach(partition -> {
					List<Integer> isrs = partition.isr().stream().map(Node::id).collect(Collectors.toList());
					List<Integer> replica = partition.replicas().stream().map(Node::id).collect(Collectors.toList());
					TopicDescDto descDto = TopicDescDto.builder().partition(partition.partition())
							.leader(partition.leader().id()).replicas(replica).isr(isrs).build();
					descDtos.add(descDto);
				});
				topic.setReplicationFactor(descDtos.get(0).getReplicas().size());
				topic.setTopicDesc(descDtos);
				topicDetails.add(topic);
			}
		} catch (InterruptedException | ExecutionException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		return topicDetails;
	}

	private AdminClient getAdminClient() {
		Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return AdminClient.create(properties);
	}

}
