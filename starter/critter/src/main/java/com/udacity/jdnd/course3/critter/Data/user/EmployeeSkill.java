package com.udacity.jdnd.course3.critter.Data.user;

import org.springframework.beans.factory.annotation.Lookup;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A example list of employee skills that could be included on an employee or a schedule request.
 */
@Table
public enum EmployeeSkill {
    PETTING, WALKING, FEEDING, MEDICATING, SHAVING;
}
