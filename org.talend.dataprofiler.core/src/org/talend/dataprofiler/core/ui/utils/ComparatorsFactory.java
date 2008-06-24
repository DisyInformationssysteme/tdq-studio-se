// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.talend.dataprofiler.core.factory.ModelElementFileFactory;
import org.talend.dataprofiler.core.ui.editor.preview.IndicatorCommonUtil;
import org.talend.dataprofiler.core.ui.editor.preview.IndicatorUnit;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * The factory to build comparator.
 */
/**
 * DOC zqin class global comment. Detailled comment
 */
public final class ComparatorsFactory {

    private ComparatorsFactory() {
    }

    public static final int FILEMODEL_COMPARATOR_ID = 0;

    public static final int MODELELEMENT_COMPARATOR_ID = 1;

    public static final int TEXT_STATISTICS_COMPARATOR_ID = 2;

    /**
     * DOC zqin Comment method "sort".
     * 
     * @param objects
     * @param comparatorId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object[] sort(Object[] objects, int comparatorId) {
        if (objects == null || objects.length <= 1) {
            return objects;
        }
        Arrays.sort(objects, ComparatorsFactory.buildComparator(comparatorId));
        return objects;
    }

    /**
     * DOC zqin Comment method "sort".
     * 
     * @param objects
     * @param comparatorId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Collection sort(List objects, int comparatorId) {
        if (objects == null || objects.size() <= 1) {
            return objects;
        }
        Collections.sort(objects, ComparatorsFactory.buildComparator(comparatorId));
        return objects;
    }

    @SuppressWarnings("unchecked")
    public static Comparator buildComparator(int comparatorId) {
        switch (comparatorId) {
        case FILEMODEL_COMPARATOR_ID:
            return new FileModelComparator();
        case MODELELEMENT_COMPARATOR_ID:
            return new ModelElementComparator();
        case TEXT_STATISTICS_COMPARATOR_ID:
            return new TextStatisticsComparator();
        default:
            return new ModelElementComparator();
        }
    }

    /**
     * Implements Comparator to implement custom sorting of {@link IFile} which contains {@link ModelElement} elements.
     */
    static class FileModelComparator implements Comparator<IFile> {

        public int compare(IFile arg0, IFile arg1) {

            if (arg0 == null || arg1 == null) {
                return 0;
            }
            ModelElement modelElement0 = ModelElementFileFactory.getModelElement(arg0);
            ModelElement modelElement1 = ModelElementFileFactory.getModelElement(arg1);
            if (modelElement0 == null || modelElement1 == null) {
                return 0;
            }
            String name0 = modelElement0.getName();
            String name1 = modelElement1.getName();

            if (name0 == null || name1 == null) {
                return 0;
            }

            return name0.compareTo(name1);
        }

    }

    /**
     * Implements Comparator to implement custom sorting of {@link ModelElement}.
     */
    static class ModelElementComparator implements Comparator<Object> {

        public int compare(Object arg0, Object arg1) {

            if (arg0 == null || arg1 == null) {
                return 0;
            }
            String name0 = ((ModelElement) arg0).getName();
            String name1 = ((ModelElement) arg1).getName();

            if (name0 == null || name1 == null) {
                return 0;
            }

            return name0.compareTo(name1);
        }
    }

    /**
     * DOC zqin ComparatorsFactory class global comment. Detailled comment
     */
    static class TextStatisticsComparator implements Comparator<IndicatorUnit> {

        public int compare(IndicatorUnit o1, IndicatorUnit o2) {

            IndicatorCommonUtil.compositeIndicatorMap(o1);
            IndicatorCommonUtil.compositeIndicatorMap(o2);

            double value1 = Double.parseDouble(String.valueOf(o1.getValue()));
            double value2 = Double.parseDouble(String.valueOf(o2.getValue()));
            if (value1 <= value2) {
                return -1;
            } else {
                return 1;
            }
        }

    }
}
