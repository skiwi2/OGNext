package com.ognext

class RecycleReport extends Report {
    static constraints = {
        key unique: true
    }

    @Override
    String toString() {
        "${this.class.simpleName}($id, $player, $key)"
    }
}
