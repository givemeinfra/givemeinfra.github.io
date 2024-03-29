/*******************************************************************************
 * Copyright (C) 2010 Robert Munteanu <robert.munteanu@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.itsolut.mantis.core;

import java.util.*;

import org.eclipse.core.runtime.Assert;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.osgi.util.NLS;

import com.itsolut.mantis.core.MantisAttributeMapper.Attribute;
import com.itsolut.mantis.core.TaskRelationshipChange.Direction;
import com.itsolut.mantis.core.model.MantisRelationship;
import com.itsolut.mantis.core.util.MantisUtils;

/**
 * 
 * @author Robert Munteanu
 */
public class TaskRelationshipChangeFinder {

    private final MantisTaskDataHandler _mantisTaskDataHandler;

    public TaskRelationshipChangeFinder(MantisTaskDataHandler mantisTaskDataHandler) {

        _mantisTaskDataHandler = mantisTaskDataHandler;
    }

    /**
     * Returns a sorted list of detected changes, possibly empty
     * 
     * <p>The list will contain all the found deletions and then all the found additions. This will
     * insure that the change list can be processed as-is, without additional sorting.</p>
     * 
     * @param taskData
     * @param changedAttributes
     * @return a sorted list of detected changes, possibly empty
     */
    public List<TaskRelationshipChange> findChanges(TaskData taskData, Set<TaskAttribute> changedAttributes) {

        Assert.isNotNull(taskData);
        Assert.isNotNull(changedAttributes);
        
        if ( changedAttributes.size() > 0 && taskData.isNew() )
            throw new RuntimeException(NLS.bind("Task data is new but changedAttributes.size is {0}.", changedAttributes.size()));


        List<TaskRelationshipChange> changes = new ArrayList<TaskRelationshipChange>();

        for (Attribute relationAttribute : MantisAttributeMapper.taskRelationAttributes()) {

            TaskAttribute parentAttribute = taskData.getRoot().getAttribute(relationAttribute.getKey());
            TaskAttribute oldAttribute = null;
            for (TaskAttribute attribute : changedAttributes) {
                if (attribute.getId().equals(relationAttribute.getKey())) {
                    oldAttribute = attribute;
                    break;
                }
            }
            
            if (oldAttribute == null && !taskData.isNew())
                continue;
            
            List<String> newValues = parentAttribute != null ? unwrapValues(parentAttribute.getValues()) : Collections.<String> emptyList();
            Map<String,String> oldIdToValues = taskData.isNew() ? Collections.<String, String> emptyMap() : findOldValues(oldAttribute);

            changes.addAll(findRemovedValues(relationAttribute, newValues, oldIdToValues));
            changes.addAll(findAddedValues(relationAttribute, newValues, new ArrayList<String>(oldIdToValues.values())));
        }

        // place deletions first, as this is the natural processing order 
        Collections.sort(changes, new Comparator<TaskRelationshipChange>() {

            public int compare(TaskRelationshipChange o1, TaskRelationshipChange o2) {

                if ( o1.getDirection() == o2.getDirection() )
                    return 0;
                
                if ( o1.getDirection() == Direction.Added )
                    return 1;
                
                return -1;
            }
        });
        
        return changes;
    }

    /**
     * This method works around the fact that we sometimes receive values a CSV strings, 
     * and sometimes as lists of strings
     */
    private List<String> unwrapValues(List<String> values) {

        List<String> results = new ArrayList<String>();

        for (String value : values)
            if (value.indexOf(',') == -1)
                results.add(value);
            else
                results.addAll(fromCsvString(value));
        
        return results;
    }

    private List<String> fromCsvString(String value) {
        
        if ( MantisUtils.isEmpty(value) )
            return Collections.emptyList();

        String[] raw = value.split("\\,");
        List<String> values = new ArrayList<String>(raw.length);
        for (String rawValue : raw)
            values.add(rawValue.trim());

        return values;
    }
    
    private Map<String,String> findOldValues(TaskAttribute oldAttribute) {
        
        List<String> oldValues = oldAttribute.getValues();
        List<String> oldIds = fromCsvString(oldAttribute.getMetaData().getValue(MantisAttributeMapper.TASK_ATTRIBUTE_RELATIONSHIP_IDS));

        Assert.isTrue(oldValues.size() == oldIds.size(), NLS.bind("Inconsistency when reading old attribute values for {0}. oldValues: {1}, oldIds: {2}.", new Object[] { oldAttribute.getId(), oldValues, oldIds }));
        
        Map<String,String> oldValuesById = new HashMap<String, String>();
        
        for ( int i = 0; i < oldValues.size(); i++ )
            oldValuesById.put(oldIds.get(i), oldValues.get(i));
        
        return oldValuesById;
        
    }

    private List<TaskRelationshipChange> findRemovedValues(Attribute relationAttribute, List<String> newValues,
            Map<String, String> oldIdToValues) {

        List<TaskRelationshipChange> changed = new ArrayList<TaskRelationshipChange>();

        for (Map.Entry<String,String> oldValueEntry : oldIdToValues.entrySet()) {

            if (!(newValues.contains(oldValueEntry.getValue())))
                changed.add(new TaskRelationshipChange(Direction.Removed, createRelationship(relationAttribute, Integer.parseInt(oldValueEntry.getKey()),
                        oldValueEntry.getValue())));
        }
        
        return changed;
    }

    private MantisRelationship createRelationship(Attribute relationAttribute, int relationshipId, String targetId) {

        MantisRelationship relationship = new MantisRelationship();

        relationship.setId(relationshipId);
        relationship.setTargetId(Integer.parseInt(targetId));
        relationship.setType(_mantisTaskDataHandler.getRelationTypeForAttribute(relationAttribute));

        return relationship;

    }

    private List<TaskRelationshipChange> findAddedValues(Attribute relationAttribute, List<String> newValues,
            List<String> oldValues) {

        List<TaskRelationshipChange> changed = new ArrayList<TaskRelationshipChange>();
		
		for (String fromValue : newValues) {
		
		    if (MantisUtils.isEmpty(fromValue))
		        continue;
		
		    if (!(oldValues.contains(fromValue)))
		        changed.add(new TaskRelationshipChange(Direction.Added, createRelationship(relationAttribute, 0,
		                fromValue)));
		}
		return changed;
    }

}
