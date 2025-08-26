package com.ninjaSystem.services;

import com.ninjaSystem.domain.Ninja;

public class TrainingService {
    public void basicTraining(Ninja ninja) {
        ninja.getStats().train(1, 1, 2);
    }

    public void advancedTraining(Ninja ninja) {
        ninja.getStats().train(3, 2, 5);
    }
}
