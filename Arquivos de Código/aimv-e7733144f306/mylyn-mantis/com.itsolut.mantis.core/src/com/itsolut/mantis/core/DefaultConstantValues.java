/*******************************************************************************
 * Copyright (c) 2007 - 2007 IT Solutions, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Robert Munteanu
 *******************************************************************************/
package com.itsolut.mantis.core;

public interface DefaultConstantValues {

    public enum Role {

        REPORTER(25), DEVELOPER(55), NOBODY(100);

        private final int value;

        private Role(int value) {

            this.value = value;
        }

        public int getValue() {

            return value;
        }
    }

    public enum Threshold {

        REPORT_BUG_THRESHOLD(Role.REPORTER.getValue()), UPDATE_BUG_ASSIGN_THRESHOLD(Role.DEVELOPER
                .getValue());

        private final int value;

        private Threshold(int value) {

            this.value = value;
        }

        public int getValue() {

            return value;
        }
    }

    public enum Priority {
        
        IMMEDIATE(60), URGENT(50), HIGH(40), NORMAL(30), LOW(20), NONE(10);

        private final int value;

        private Priority(int value) {

            this.value = value;
        }

        public int getValue() {

            return value;
        }

    }
    
    /**
     * Contains default values for bug, bug note and bug operations attributes.
     * 
     * @author Robert Munteanu
     *
     */
    public enum Attribute {
        
        BUG_SEVERITY(50), BUG_PRIORITY(Priority.NORMAL.getValue()), BUG_RESOLUTION(10), BUG_REPRODUCIBILITY(70), BUG_PROJECTION(10), BUG_ETA(10), BUG_VIEW_STATUS(10), BUG_RESOLUTION_FIXED_THRESHOLD(20), ETA_ENABLED(1), PROJECTION_ENABLED(1), PROFILES_ENABLED(1); 

        private final int value;

        private Attribute(int value) {

            this.value = value;
        }

        public int getValue() {

            return value;
        }


    }

    public enum Status {
        
        NEW(10), ASSIGNED(50), RESOLVED(80);
        
        private final int value;

        private Status(int value) {

            this.value = value;
        }

        public int getValue() {

            return value;
        }
    }
    
    public enum Resolution {
        
        OPEN(10);
        
        private final int value;

        private Resolution(int value) {

            this.value = value;
        }

        public int getValue() {

            return value;
        }
    }

    public enum Severity {
        
        MINOR(50);
        
        private final int value;

        private Severity(int value) {

            this.value = value;
        }

        public int getValue() {

            return value;
        }
    }

    public enum ViewState {
        PUBLIC(10), PRIVATE(50);
        
        private final int value;
        
        private ViewState(int value) {
            
            this.value = value;
        }
        
        public int getValue() {

            return value;
        }
    }
}
