package io.vkoloss.batchtest;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReaderException;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("io.vkoloss.batchtest")
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    @Autowired
    JobBuilderFactory jobs;

    @Autowired
    StepBuilderFactory steps;

    @Autowired
    ModelReader reader;

    @Autowired
    ModelProcessor processor;

    @Autowired
    ModelWriter writer;

    @Autowired
    ModelListener listener;

    @Override
    protected JobRepository createJobRepository() throws Exception {
        return new MapJobRepositoryFactoryBean().getObject();
    }

    @Bean
    public Job oracleModelMasterJob() {
        return jobs.get("oracleModelMasterJob").start(step()).build();
    }

    @Bean
    public Step step() {
        return steps.get("step").listener(listener).<Model, Model> chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .chunk(1)
                .faultTolerant()
                .skip(RuntimeException.class).noSkip(ItemReaderException.class).skipLimit(Integer.MAX_VALUE)
                .retryLimit(0)
                .build();
    }

    @Bean
    DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/batch-test");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        return ds;
    }

    @Bean
    ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

}
