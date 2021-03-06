/*
 * Copyright 2011-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import griffon.core.GriffonClass
import griffon.core.GriffonApplication
import griffon.plugins.carbonado.CarbonadoConnector
import griffon.plugins.carbonado.CarbonadoEnhancer
import griffon.plugins.carbonado.CarbonadoContributionHandler

import static griffon.util.ConfigUtils.getConfigValueAsBoolean

/**
 * @author Andres Almiray
 */
class CarbonadoGriffonAddon {
    void addonPostInit(GriffonApplication app) {
        CarbonadoConnector.instance.createConfig(app)
        def types = app.config.griffon?.carbonado?.injectInto ?: ['controller']
        for(String type : types) {
            for(GriffonClass gc : app.artifactManager.getClassesOfType(type)) {
                if (CarbonadoContributionHandler.isAssignableFrom(gc.clazz)) continue
                CarbonadoEnhancer.enhance(gc.metaClass)
            }
        }
    }

    Map events = [
        LoadAddonsEnd: { app, addons ->
            if (getConfigValueAsBoolean(app.config, 'griffon.carbonado.connect.onstartup', true)) {
                ConfigObject config = CarbonadoConnector.instance.createConfig(app)
                CarbonadoConnector.instance.connect(app, config)
            }
        },
        ShutdownStart: { app ->
            ConfigObject config = CarbonadoConnector.instance.createConfig(app)
            CarbonadoConnector.instance.disconnect(app, config)
        }
    ]
}
