package com.yuuki.game.exceptions;

/**
 * Exception used when one object is already in the map when trying to introduce it again.
 *
 * @author Yuuki
 * @date 31/08/2015 | 18:21
 * @package com.yuuki.game.exceptions
 */
public class ObjectAlreadyInMap extends Exception {

    public ObjectAlreadyInMap() {
        this("The object is already in the map.");
    }

    public ObjectAlreadyInMap(String message) {
        super(message);
    }
}
