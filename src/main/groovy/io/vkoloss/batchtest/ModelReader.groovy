package io.vkoloss.batchtest

import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component('modelReader')
class ModelReader implements ItemReader<Model> {

    InputStream xmlStream = getClass().getClassLoader().getResourceAsStream('models.xml')

    Iterator iterator

    @PostConstruct
    void init() {
        iterator = new XmlSlurper().parse(xmlStream).childNodes()
    }

    @Override
    Model read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        def attributes = iterator.next().attributes
        iterator.hasNext() ? new Model(name: attributes.name, price: attributes.price?.toLong()) : null
    }

}
