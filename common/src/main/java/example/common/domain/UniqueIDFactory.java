package example.common.domain;

import java.util.UUID;

public class UniqueIDFactory {
    public static Identity createID() {
        return new Identity(UUID.randomUUID().toString());
    }
}
