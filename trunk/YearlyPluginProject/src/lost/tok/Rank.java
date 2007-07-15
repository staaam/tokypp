/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok;

import org.dom4j.Element;

/**
 * Contains all data related to authors rank
 * @author evgeni
 *
 */
public class Rank {

	String name;
	int id;

	/**
	 * Rank class construcor
	 * @param rankName
	 * @param rankId
	 */
	public Rank(String rankName, int rankId) {
		name = rankName;
		id = rankId;
	}

	/**
	 * Rank class construcor
	 * @param rankName
	 * @param rankId
	 */
	public Rank(Element e) {
		this(e.element("name").getText(), Integer.valueOf(e.element("id").getText())); //$NON-NLS-1$ //$NON-NLS-2$
	}


	/**
	 * Get rank ID
	 * @return
	 */
	public int getId() {
		return id;
	}

	
	/**
	 * Set rank ID
	 * @param rankId
	 */
	public void setId(int rankId) {
		id = rankId;
	}

	
	/**
	 * Get rank name
	 * @return
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Set rank name
	 * @param rankId
	 */
	public void setName(String rankName) {
		name = rankName;
	}
}

