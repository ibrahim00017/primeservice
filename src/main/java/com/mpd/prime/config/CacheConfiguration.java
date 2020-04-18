package com.mpd.prime.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration caffeineConfiguration = new CaffeineConfiguration();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mpd.prime.domain.Banque.class.getName());
            createCache(cm, com.mpd.prime.domain.Banque.class.getName() + ".comptes");
            createCache(cm, com.mpd.prime.domain.Compte.class.getName());
            createCache(cm, com.mpd.prime.domain.Grade.class.getName());
            createCache(cm, com.mpd.prime.domain.Direction.class.getName());
            createCache(cm, com.mpd.prime.domain.Corps.class.getName());
            createCache(cm, com.mpd.prime.domain.Prime.class.getName());
            createCache(cm, com.mpd.prime.domain.Trimestre.class.getName());
            createCache(cm, com.mpd.prime.domain.Annee.class.getName());
            createCache(cm, com.mpd.prime.domain.Agent.class.getName());
            createCache(cm, com.mpd.prime.domain.Agent.class.getName() + ".comptes");
            createCache(cm, com.mpd.prime.domain.Fonction.class.getName());
            createCache(cm, com.mpd.prime.domain.Promotion.class.getName());
            createCache(cm, com.mpd.prime.domain.Allouer.class.getName());
            createCache(cm, com.mpd.prime.domain.Avancement.class.getName());
            createCache(cm, com.mpd.prime.domain.Changement.class.getName());
            createCache(cm, com.mpd.prime.domain.Affectation.class.getName());
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
