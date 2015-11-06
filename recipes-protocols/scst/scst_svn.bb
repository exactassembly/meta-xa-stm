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
SUMMARY="Linux SCSI Target Mode drivers and tools"
LICENSE="GPLv2"
LIC_FILES_CHKSUM = "file://scst/COPYING;md5=54facb70adf7e4bb1895398fd8759dd7"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

RDEPENDS_${PN} = " \
    perl \
    perl-module-fcntl \
    perl-module-io-dir \
    perl-module-io-file \
    perl-module-getopt-long \
    perl-module-posix \
    perl-module-overloading \
"

DEPENDS=" \
    virtual/kernel \
    perl \
"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

SRC_URI="svn://svn.code.sf.net/p/scst/svn;module=trunk"
SRCREV="${AUTOREV}"

SRC_URI_append = " \
    file://0001-add-mpt3.patch;striplevel=0 \
    file://scst.conf \
"

PR="2"

S="${WORKDIR}/trunk"

inherit module-base kernel-module-split

INSANE_SKIP_${PN} = "installed-vs-shipped"

export STAGING_KERNEL_DIR

FILES_${PN}-dev += " \
    /include/scst/* \
"

FILES_${PN} += " \
    /lib/modules/* \
    /man/* \
    /usr/lib/perl5/* \
"

do_unpack_mpt3() {
    rm -f ${S}/mpt3/mpi
    rm -f ${S}/mpt3/mpt3sas_stm.h
    ln -s ${STAGING_KERNEL_DIR}/drivers/scsi/mpt3sas/mpi ${S}/mpt3/mpi
    ln -s ${STAGING_KERNEL_DIR}/drivers/scsi/mpt3sas/mpt3sas_stm.h ${S}/mpt3/mpt3sas_stm.h
}
addtask unpack_mpt3 after do_unpack before do_configure

addtask make_scripts after do_configure before do_compile

EXTRA_OEMAKE = " \
    'EXTRA_CFLAGS=-I${S}/scst/include -DCONFIG_SCST_DEBUG -DCONFIG_SCST_TRACING' \
"

do_configure() {
}

do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake KERNEL_PATH=${STAGING_KERNEL_DIR}   \
        KERNEL_SRC=${STAGING_KERNEL_DIR}    \
        KDIR=${STAGING_KERNEL_DIR} \
        INSTALL_MOD_PATH=${D} \
        DESTDIR=${D} \
        PREFIX=/usr \
        CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
        AR="${KERNEL_AR}" scst scstadm mpt3
}

do_install() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake INSTALL_MOD_PATH=${D} \
        KERNEL_PATH=${STAGING_KERNEL_DIR}   \
        KERNEL_SRC=${STAGING_KERNEL_DIR}    \
        KDIR=${STAGING_KERNEL_DIR} \
        DESTDIR=${D} \
        PREFIX=/usr \
        CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
        AR="${KERNEL_AR}" \
        scst_install scstadm_install mpt3_install

    install -d ${D}/etc
    install -m 0644 ${WORKDIR}/scst.conf ${D}/etc
    install -d ${D}/usr/lib/perl5/5.22.0/SCST
    install -m 0644 ${S}/scstadmin/scstadmin.sysfs/scst-0.9.10/lib/SCST/SCST.pm ${D}/usr/lib/perl5/5.22.0/SCST/SCST.pm

    install -d ${D}${sysconfdir}/rc5.d
    ln -s ../init.d/scst ${D}${sysconfdir}/rc5.d/S99scst

}
