package io.vkoloss.batchtest

import org.springframework.batch.core.listener.ItemListenerSupport
import org.springframework.stereotype.Component

@Component("listener")
class ModelListener extends ItemListenerSupport<Model, Model> {

    @Override
    public void onReadError(Exception ex) {
        println "Encountered error on read due to $ex"
    }

    @Override
    public void onWriteError(Exception ex, List<? extends Model> item) {
        println "Encountered error on write $item due to $ex"
    }
}
