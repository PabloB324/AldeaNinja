package com.ninjaSystem.services;

import com.ninjaSystem.domain.Ninja;

public class TrainingService {
    public void basicTraining(Ninja ninja) {
        System.out.println(ninja.getName() + " entrena intensamente...");
        ninja.getStats().train(1, 1, 2);
    }

    public void advancedTraining(Ninja ninja) {
        System.out.println(ninja.getName() + " hace un entrenamiento avanzado!");
        ninja.getStats().train(3, 2, 5);
    }
}
