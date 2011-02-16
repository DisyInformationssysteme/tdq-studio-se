// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.wizard.dqrules;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.talend.core.model.properties.Item;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.cwm.relational.TdExpression;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.editor.dqrules.DQRuleEditor;
import org.talend.dataprofiler.core.ui.wizard.AbstractWizard;
import org.talend.dataquality.indicators.definition.IndicatorCategory;
import org.talend.dataquality.properties.TDQBusinessRuleItem;
import org.talend.dataquality.rules.WhereRule;
import org.talend.dq.analysis.parameters.DQRulesParameter;
import org.talend.dq.dqrule.DqRuleBuilder;
import org.talend.dq.helper.resourcehelper.DQRuleResourceFileHelper;
import org.talend.dq.helper.resourcehelper.ResourceFileMap;
import org.talend.dq.indicators.definitions.DefinitionHandler;
import org.talend.dq.writer.impl.ElementWriterFactory;
import org.talend.resource.ResourceManager;
import org.talend.utils.sugars.TypedReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC xqliu class global comment. Detailled comment
 */
public class NewDQRulesWizard extends AbstractWizard {

    static Logger log = Logger.getLogger(NewDQRulesWizard.class);

    private NewDQRulesWizardPage1 mPage;

    private NewDQRulesWizardPage2 mPage2;

    private DQRulesParameter parameter;

    private TdExpression expression;

    // default value of Criticality Level
    private static final int CRITICALITY_LEVEL_DEFAULT = 1;

    public NewDQRulesWizard(DQRulesParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public void addPages() {
        String s = DefaultMessagesImpl.getString("NewDQRulesWizard.dqRule"); //$NON-NLS-1$

        mPage = new NewDQRulesWizardPage1();
        mPage.setTitle(s + DefaultMessagesImpl.getString("NewDQRulesWizard.createPage1_2")); //$NON-NLS-1$
        mPage.setDescription(DefaultMessagesImpl.getString("NewDQRulesWizard.defineProp")); //$NON-NLS-1$
        mPage.setPageComplete(false);

        mPage2 = new NewDQRulesWizardPage2();
        mPage2.setTitle(s + DefaultMessagesImpl.getString("NewDQRulesWizard.createPage2_2")); //$NON-NLS-1$
        mPage2.setDescription(DefaultMessagesImpl.getString("NewDQRulesWizard.defineWhere")); //$NON-NLS-1$

        addPage(mPage);
        addPage(mPage2);
    }

    public TypedReturnCode<Object> createAndSaveCWMFile(ModelElement cwmElement) {
        WhereRule whereRule = (WhereRule) cwmElement;

        TaggedValueHelper.setValidStatus(true, whereRule);
        whereRule.setWhereExpression(parameter.getWhereClause());
        whereRule.setCriticalityLevel(CRITICALITY_LEVEL_DEFAULT);
        whereRule.getSqlGenericExpression().add(getExpression());

        // MOD scorreia 2009-04-29 bug 7151: add the category
        IndicatorCategory ruleIndicatorCategory = DefinitionHandler.getInstance().getDQRuleIndicatorCategory();
        if (ruleIndicatorCategory != null && !whereRule.getCategories().contains(ruleIndicatorCategory)) {
            whereRule.getCategories().add(ruleIndicatorCategory);
        }

        IFolder folder = parameter.getFolderProvider().getFolderResource();
        return ElementWriterFactory.getInstance().createdRuleWriter().create(whereRule, folder);
    }

    public ModelElement initCWMResourceBuilder() {
        DqRuleBuilder ruleBuilder = new DqRuleBuilder();

        boolean ruleInitialized = ruleBuilder.initializeDqRuleBuilder(parameter.getName());
        if (ruleInitialized) {
            return ruleBuilder.getWhereRule();
        }
        return null;
    }

    public TdExpression getExpression() {
        if (expression == null) {
            expression = (TdExpression) EcoreUtil.copy(DefinitionHandler.getInstance().getDQRuleDefaultIndicatorDefinition()
                    .getSqlGenericExpression().get(0));
        }
        return expression;
    }

    @Override
    protected String getEditorName() {
        return DQRuleEditor.class.getName();
    }

    @Override
    protected DQRulesParameter getParameter() {
        return parameter;
    }

    @Override
    protected ResourceFileMap getResourceFileMap() {
        return DQRuleResourceFileHelper.getInstance();
    }

    @Override
    public boolean canFinish() {
        if (mPage2 != null) {
            if (getParameter().getWhereClause() != null) {
                if (getParameter().getWhereClause() != null && !"".equals(getParameter().getWhereClause())) { //$NON-NLS-1$
                    return mPage2.isPageComplete();
                }
            }
        }
        return false;
    }

    @Override
    public void openEditor(Item item) {
        TDQBusinessRuleItem ruleItem = (TDQBusinessRuleItem) item;
        String folderPath = ruleItem.getState().getPath();
        if (folderPath.equals("")) {
            folderPath = ResourceManager.getRulesSQLFolder().toString();
        }
        Path path = new Path(folderPath);

        Path append = (Path) path.append(new Path(ruleItem.getFilename()));
        IPath removeLastSegments = append.removeFirstSegments(2);
        IFile fileEditorInput = ResourceManager.getRootProject().getFile(removeLastSegments);
        try {
            IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), fileEditorInput, true);
        } catch (PartInitException e) {
            log.error(e, e);
        }
        // BusinessRuleItemEditorInput dqRuleEditorInput = new BusinessRuleItemEditorInput(item);
        // CorePlugin.getDefault().openEditor(dqRuleEditorInput, DQRuleEditor.class.getName());
    }
}
