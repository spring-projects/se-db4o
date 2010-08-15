package org.springextensions.db4o.config;

import com.db4o.diagnostic.DiagnosticConfiguration;

/**
 * TODO
 *
 * @author olli
 */
public class DiagnosticConfigurer {

    protected DiagnosticConfiguration diagnosticConfiguration;

    public DiagnosticConfigurer(DiagnosticConfiguration diagnosticConfiguration) {
        this.diagnosticConfiguration = diagnosticConfiguration;
    }

}
