import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource

beans {

    xmlns context:"http://www.springframework.org/schema/context"
    xmlns batch:"http://www.springframework.org/schema/batch"

    context.'component-scan'('base-package': 'io.vkoloss.batchtest')

    dataSource(DriverManagerDataSource) {
        driverClassName = "org.postgresql.Driver"
        url = "jdbc:postgresql://localhost:5432/test"
        username = "postgres"
        password = "postgres"
    }

    transactionManager(ResourcelessTransactionManager)

    jdbcTemplate(JdbcTemplate) {
        dataSource = dataSource
    }

	jobRepository(MapJobRepositoryFactoryBean) {
        //dataSource = dataSource
        transactionManager = transactionManager
        //databaseType = 'POSTGRES'
    }

    jobLauncher(SimpleJobLauncher) {
        jobRepository = jobRepository
    }

    batch.job(id: 'itemJob') {
        batch.step(id: 'stepOne') {
            batch.tasklet {
                batch.chunk(
                    reader: 'modelReader',
                    writer: 'modelWriter',
                    processor: 'modelProcessor',
                    'commit-interval': 2,
                    'skip-limit': 3
                ) {
                    batch.'skippable-exception-classes' {
                        batch.include(
                                class: 'java.lang.RuntimeException'
                        )
                    }
                }
            }
            batch.listeners {
                batch.listener {
                    bean(
                        'class': 'io.vkoloss.batchtest.ModelListener'
                    )
                }
            }
        }
    }

}
