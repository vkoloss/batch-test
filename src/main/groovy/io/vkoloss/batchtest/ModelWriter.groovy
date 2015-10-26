package io.vkoloss.batchtest

import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component('modelWriter')
class ModelWriter implements ItemWriter<Model> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    void write(List<? extends Model> items) throws Exception {
        println "writing " + items.size() + " elements in batch"
        items.each {
            println "writing $it"
        }
        jdbcTemplate.batchUpdate("insert into model values (nextval('model_seq'), ?, ?)", items.collect {
            [it.name, it.price] as Object[]
        })
    }
}
