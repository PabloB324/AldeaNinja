package com.ninjaSystem.patterns.factory;

import com.ninjaSystem.domain.Ninja;
import com.ninjaSystem.domain.Rank;

public interface AbstractNinjaFactory {
    Ninja createNinja(String name,Rank rank);
}
