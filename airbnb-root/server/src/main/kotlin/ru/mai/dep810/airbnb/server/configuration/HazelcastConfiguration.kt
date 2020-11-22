package ru.mai.dep810.airbnb.server.configuration

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HazelcastConfiguration {
    @Bean(destroyMethod = "shutdown")
    fun hazelcastInstance(@Qualifier("hazelcastConfig") config: Config): HazelcastInstance =
         Hazelcast.getOrCreateHazelcastInstance(config)

    @Bean("hazelcastConfig")
    fun hazelcastConfig(): Config =
            Config("reservations")
}