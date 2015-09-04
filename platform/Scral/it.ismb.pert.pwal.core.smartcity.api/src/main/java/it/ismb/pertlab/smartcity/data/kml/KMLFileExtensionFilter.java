/*
 * SmartCityAPI - KML to N3 conversion
 * 
 * Copyright (c) 2014 Dario Bonino
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package it.ismb.pertlab.smartcity.data.kml;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author <a href="mailto:dario.bonino@gmail.com">Dario Bonino</a>
 *
 */
public class KMLFileExtensionFilter implements FilenameFilter
{

	@Override
	public boolean accept(File dir, String name)
	{
		// TODO Auto-generated method stub
		return (name.endsWith(".kml")||name.endsWith(".KML"));
	}

	
}
