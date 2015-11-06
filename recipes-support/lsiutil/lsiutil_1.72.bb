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
SUMMARY="LSISAS device configuration utility"
LICENSE="LSI Proprietary"
LIC_FILES_CHKSUM = "file://lsiutil.c;md5=4222fde02c05461ed9e0edc5ca10c066"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://lsiutil-${PV}.tar.gz"

PR="1"

S="${WORKDIR}/lsiutil"

do_compile() {
    oe_runmake lsiutil
}

do_install() {
    install -d ${D}${sbindir}
    install ${S}/lsiutil ${D}${sbindir}
}

FILES_${PN} = " ${sbindir}/lsiutil "
