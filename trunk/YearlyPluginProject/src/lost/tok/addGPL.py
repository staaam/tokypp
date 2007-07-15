import os
import sys
import os.path

GPL = \
"""/* Tree of Knowledge - An information management Eclipse plugin
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

"""

def recAddGPL(source):
    if os.path.isdir(source):
        for aFile in os.listdir(source):
            recAddGPL(source + os.sep + aFile)
    elif os.path.isfile(source) and source.endswith(".java"):
        f = open(source)
        code = f.read()
        f.close()
        newCode = GPL + code
        print "Adding GPL shit to",source
        f = open(source, 'w')
        f.write(newCode)
        f.close
                        
recAddGPL('.')
