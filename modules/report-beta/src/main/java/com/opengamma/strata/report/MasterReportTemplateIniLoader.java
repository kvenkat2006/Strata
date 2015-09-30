/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.strata.report;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.collect.Messages;
import com.opengamma.strata.collect.io.IniFile;
import com.opengamma.strata.collect.io.PropertySet;
import com.opengamma.strata.report.cashflow.CashFlowReportTemplateIniLoader;
import com.opengamma.strata.report.trade.TradeReportTemplateIniLoader;

/**
 * Loads report templates from .ini files by delegating to specific loaders for the different report types.
 */
class MasterReportTemplateIniLoader {

  private static final Set<ReportTemplateIniLoader<? extends ReportTemplate>> LOADERS = ImmutableSet.of(
      new TradeReportTemplateIniLoader(),
      new CashFlowReportTemplateIniLoader());

  private MasterReportTemplateIniLoader() {
  }

  /**
   * Loads a report template from an .ini file.
   *
   * @param iniFile  the .ini file containing the definition of a report template
   * @return the template defined in the .ini file
   * @throws RuntimeException if the ini file cannot be parsed
   */
  public static ReportTemplate load(IniFile iniFile) {
    String settingsSectionKey = iniFile.sections().stream()
        .filter(k -> k.toLowerCase().equals(ReportTemplateIniLoader.SETTINGS_SECTION))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            Messages.format("Report template INI file must contain a {} section", ReportTemplateIniLoader.SETTINGS_SECTION)));
    PropertySet settingsSection = iniFile.section(settingsSectionKey);
    String reportType = settingsSection.value(ReportTemplateIniLoader.SETTINGS_REPORT_TYPE);
    ReportTemplateIniLoader<? extends ReportTemplate> iniLoader = LOADERS.stream()
        .filter(loader -> loader.getReportType().toLowerCase().equals(reportType.toLowerCase()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(Messages.format("Unsupported report type: {}", reportType)));
    return iniLoader.load(iniFile);
  }

}
