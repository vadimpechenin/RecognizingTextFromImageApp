package db;

import java.util.List;

public interface EntityService<T> {

    List<T> findAll();
}
