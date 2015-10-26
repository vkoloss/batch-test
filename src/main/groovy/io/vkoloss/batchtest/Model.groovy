package io.vkoloss.batchtest

class Model {
    String name
    BigDecimal price

    @Override
    public String toString() {
        "Model{name='$name', price=$price}"
    }
}
