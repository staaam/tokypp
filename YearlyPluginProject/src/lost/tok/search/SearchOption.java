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

package lost.tok.search;

/**
 * Contains enumeration of all options availible
 * in search dialog page as checkboxes
 * 
 * @author Michael Gelfand
 *
 */
public enum SearchOption {
	CASE_SENSITIVE,
	SRC_TITLE, SRC_AUTHOR, SRC_CONTENT,
	DSC_NAME, DSC_CREATOR,
	DSC_OPINIONS, DSC_QUOTES, DSC_QUOTE_COMMENTS, 
}