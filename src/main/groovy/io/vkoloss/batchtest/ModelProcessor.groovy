package io.vkoloss.batchtest

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component('modelProcessor')
class ModelProcessor implements ItemProcessor<Model, Model> {
    @Override
    Model process(Model item) throws Exception {
        return item
    }
}
