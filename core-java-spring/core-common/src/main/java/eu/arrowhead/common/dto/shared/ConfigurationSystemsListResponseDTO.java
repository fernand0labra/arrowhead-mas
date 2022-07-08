/********************************************************************************
 * Copyright (c) 2021 {Lulea University of Technology}
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   {Lulea University of Technology} - implementation
 *   Arrowhead Consortia - conceptualization
 ********************************************************************************/
package eu.arrowhead.common.dto.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationSystemsListResponseDTO implements Serializable {

        //=================================================================================================
        // members
        
        private static final long serialVersionUID = 2359853742228146773L;
        
        private int count;
        private List<String> systems = new ArrayList<>();
                
        //=================================================================================================
        // methods
        
        //-------------------------------------------------------------------------------------------------
        public ConfigurationSystemsListResponseDTO() {}
        public ConfigurationSystemsListResponseDTO(int count,List<String> systems) {
            this.count = count;
            this.systems = systems;
        }
        
        //-------------------------------------------------------------------------------------------------
        public int getCount() { return count; }
        public List<String> getSystems() { return systems; }

        //-------------------------------------------------------------------------------------------------
        public void setCount(int count) { this.count = count; }
        public void setSystems(List<String> systems) { this.systems = systems; }
	
}
