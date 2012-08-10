/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.enterprise.config.modularity.command;

import com.sun.enterprise.config.modularity.ConfigModularityUtils;
import com.sun.enterprise.config.serverbeans.Cluster;
import com.sun.enterprise.config.serverbeans.Config;
import com.sun.enterprise.config.serverbeans.Domain;
import com.sun.enterprise.config.serverbeans.Server;
import org.glassfish.config.support.CommandTarget;
import org.jvnet.hk2.component.Habitat;

import java.util.StringTokenizer;

/**
 * @author Masoud Kalali
 */
public abstract class AbstractConfigModularityCommand {

    protected String replaceExpressionsWithValues(String location) {
        StringTokenizer tokenizer = new StringTokenizer(location, "/", false);
        while (tokenizer.hasMoreElements()) {
            String level = tokenizer.nextToken();
            if (level.contains("[$")) {
                String expr = location.substring(location.indexOf("$"), location.indexOf("]"));
                String value = ConfigModularityUtils.resolveExpression(expr);
                location = location.replace(expr, value);
            }
        }
        return location;
    }

    protected Config getConfigForName(String targetName, Habitat habitat, Domain domain) {

        if (CommandTarget.CONFIG.isValid(habitat, targetName)) {
            return domain.getConfigNamed(targetName);
        }
        if (CommandTarget.DAS.isValid(habitat, targetName) ||
                CommandTarget.STANDALONE_INSTANCE.isValid(habitat, targetName)) {
            Server s = domain.getServerNamed(targetName);
            return s == null ? null : domain.getConfigNamed(s.getConfigRef());
        }

        if (CommandTarget.CLUSTER.isValid(habitat, targetName)) {
            Cluster cl = domain.getClusterNamed(targetName);
            return cl == null ? null : domain.getConfigNamed(cl.getConfigRef());

        }
        return null;
    }
}