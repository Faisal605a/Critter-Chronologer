package com.udacity.jdnd.course3.critter.Data.pet;

import javax.persistence.Table;

/**
 * A example list of pet type metadata that could be included on a request to create a pet.
 */
@Table
public enum PetType {
    CAT, DOG, LIZARD, BIRD, FISH, SNAKE, OTHER;
}
