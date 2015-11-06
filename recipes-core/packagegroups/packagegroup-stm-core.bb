#
# Copyright (C) 2015 Exact Assembly, LLC  <stm@xassembly.com>
# Author: Ted Vaida
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
#
#
SUMMARY = "Core packages required for SCSI Target Mode (SAS Target)"
LICENSE = "GPLv2"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} = "\
    lsiutil \
    scst \
    kernel-module-mpt3sas \
    kernel-module-mpt3sas-stm \
"
