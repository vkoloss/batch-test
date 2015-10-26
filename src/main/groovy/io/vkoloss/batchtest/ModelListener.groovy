package io.vkoloss.batchtest

import org.springframework.batch.core.listener.ItemListenerSupport

class ModelListener extends ItemListenerSupport {

    @Override
    public void onReadError(Exception ex) {
        println "Encountered error on read due to $ex"
    }

    @Override
    public void onWriteError(Exception ex, List<?> item) {
        println "Encountered error on write $item due to $ex"
    }
}
