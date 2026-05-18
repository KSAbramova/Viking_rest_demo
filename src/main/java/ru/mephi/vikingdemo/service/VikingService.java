package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.EquipmentItem;
import ru.mephi.vikingdemo.model.HairColor;

@Service
public class VikingService {
    // каждый раз при изменении создаётся новая копия списка 
    private final CopyOnWriteArrayList<Viking> vikings = new CopyOnWriteArrayList<>();
    private final VikingFactory vikingFactory;
    @Autowired
    public VikingService(VikingFactory vikingFactory) {
        this.vikingFactory = vikingFactory;
    }
    
    public List<Viking> findAll() {
        return List.copyOf(vikings);
    }

    public Viking createRandomViking() {
        

        Viking viking = vikingFactory.createRandomViking();

        vikings.add(viking);
        return viking;
    }

    public Viking addVikingGUI(String name, int age, int height, HairColor hair, BeardStyle beard) {
        
        Viking viking = vikingFactory.createCustomViking(name, age, height, hair, beard);
        
        boolean exists = vikings.stream()
                .anyMatch(v -> v.name().equalsIgnoreCase(viking.name()));

        if (exists) {
            throw new RuntimeException("Viking with name " + viking.name() + " already exists");
        }

        vikings.add(viking);
        return viking;
    }
    
    public Viking addViking(Viking viking) {
        boolean exists = vikings.stream()
                .anyMatch(v -> v.name().equalsIgnoreCase(viking.name()));

        if (exists) {
            throw new RuntimeException("Viking with name " + viking.name() + " already exists");
        }

        vikings.add(viking);
        return viking;
    }
     
    public void deleteViking(String name) {
        int index = -1;
        for (int i = 0; i < vikings.size(); i++) {
            if (vikings.get(i).name().equalsIgnoreCase(name)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            throw new RuntimeException("Viking with name " + name + " not found");
        }
    }

    
    public Viking updateViking(String name, Viking updateViking) { 
        int index = -1;
        for (int i = 0; i < vikings.size(); i++) {
            if (vikings.get(i).name().equalsIgnoreCase(updateViking.name())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new RuntimeException("Viking with name " + updateViking.name() + " not found");
        }
        
        Viking updatedViking = vikingFactory.updateViking(updateViking);
        vikings.set(index, updatedViking);
        return updatedViking;
    }
    
    public Viking updateVikingGUI(String name, int age, int height, HairColor hair, BeardStyle beard, List<EquipmentItem> equipment) { 
        int index = -1;
        for (int i = 0; i < vikings.size(); i++) {
            if (vikings.get(i).name().equalsIgnoreCase(name)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new RuntimeException("Viking with name " + name + " not found");
        }
        
        Viking updatedViking = vikingFactory.updateVikingGUI(name, age, height, hair, beard, equipment);
        vikings.set(index, updatedViking);
        return updatedViking;
    }
}