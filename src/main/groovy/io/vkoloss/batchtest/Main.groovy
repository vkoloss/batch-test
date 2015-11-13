package io.vkoloss.batchtest

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericGroovyApplicationContext

class Main {
    static void main(String[] args) {
//        def context = new GenericGroovyApplicationContext('classpath:batchConfig.groovy')
        def context = new AnnotationConfigApplicationContext(BatchConfig)
        def launcher = context.getBean(JobLauncher)
        def job = context.getBean(Job)
        launcher.run(job, new JobParameters())
    }
}
