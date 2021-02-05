package viceCity.repositories.interfaces;

import viceCity.models.guns.BaseGun;
import viceCity.models.guns.Gun;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GunRepository<Gun> implements Repository<Gun> {
    private List<Gun> models;

    private GunRepository() {
        models = new ArrayList<>();
    }

    @Override
    public Collection<Gun> getModels() {
        return Collections.unmodifiableList(this.models);
    }

    @Override
    public void add(Gun model) {
        if (!models.contains(model)) {
            models.add(model);
        }
    }

    @Override
    public boolean remove(Gun model) {
        if (models.contains(model)) {
            models.remove(model);
            return true;
        }
        return false;
    }

    @Override
    public Gun find(String name) {
        for (Gun model : models) {
            BaseGun gun = (BaseGun) model;
            if (((BaseGun) model).getName().equals(name)) {
                return model;
            }
        }
        return null;
    }
}
