SCSI Target Mode for LSISAS2 and LSISAS3 Host Bus Adapters
==========================================================

This set of files provides support for adding SCSI Target Mode (STM) support to
Yocto (https://www.yoctoproject.org/) based targets.

If you want to use the SCST and Linux kernel patches outside of Yocto, look at
the following two patch files:

> recipes-kernel/linux/linux-stable/linux-stable-4.1-mpt3sas_stm.patch

> recipes-protocols/scst/files/0001-add-mpt3.patch

Requirements
------------

### Supported HBAs
SAS2.x (6Gbps)
* 9200-8e
* 9200-4i4e
* Any device based on the LSISAS2008 chipset with LSI IT firmware

SAS3.x (12Gbps)
* 9300-8e
* 9300-4i4e
* Any device based on the LSISAS3008 chipset with LSI IT firmware

### Supported versions of Yocto
This layer is known to work with Yocto version 1.7 and 1.8, and is maintained
against the poky 'master' branch.

### Supported Linux kernels
This layer is known to work with linux-stable 4.1.3, and may work with any
relatively recent Linux branch depending on the status of the LSI mpt3sas 
driver code.

### Supported version of SCST
This layer patch is known to work against SCST 3.0 

Configuration
-------------

### HBA Configuration
In addition to having a compatible HBA, you must configure the HBA to enable
Target SCSI operations. To do this, you must use the 'lsiutil' command line
tool which is included with the packagegroup-stm-core package defined in this
layer. See Appendix 1 below for specifics on using the lsiutil command.

### Yocto Configuration
In order for Yocto to use this layer, you must add it to your layers.conf
file.



Appendix 1: Configuring HBAs with lsituil
=========================================
The lsiutil utility allows you to edit NVDATA paramaters on the HBA to 
activate hardware and firmware Target I/O capabilities. In order to operate
correctly, each PHY to be connected to a remote host must be part of a distinct
port, and only a single port is supported in the current patch set.

### Example 
Here is an example of how one HBA has been configured with a single x4 port for
normal Initator traffic, and a second x4 port for Target traffic:

> root@demox86-64:~# lsiutil
> 
> LSI Logic MPT Configuration Utility, Version 1.72, Sep 09, 2014
> modprobe: module mptctl not found in modules.dep
> 
> 1 MPT Port found
> 
> Port Name         Chip Vendor/Type/Rev    MPT Rev  Firmware Rev  IOC
> 1.  ioc0              LSI Logic SAS3008 C0      205      01000200     0
> 
> Select a device:  [1-1 or 0 to quit] 1
> 
> 1.  Identify firmware, BIOS, and/or FCode
> 2.  Download firmware (update the FLASH)
> 4.  Download/erase BIOS and/or FCode (update the FLASH)
> 8.  Scan for devices
> 801.  Scan for 1 LUN
> 810.  Scan for 10 LUN's
> 10.  Change IOC settings (interrupt coalescing)
> 13.  Change SAS IO Unit settings
> 16.  Display attached devices
> 20.  Diagnostics
> 21.  RAID actions
> 23.  Reset target
> 42.  Display operating system names for devices
> 43.  Diagnostic Buffer actions
> 45.  Concatenate SAS firmware and NVDATA files
> 59.  Dump PCI config space
> 60.  Show non-default settings
> 61.  Restore default settings
> 66.  Show SAS discovery errors
> 69.  Show board manufacturing information
> 97.  Reset SAS link, HARD RESET
> 98.  Reset SAS link
> 99.  Reset port
> e   Enable expert mode in menus
> p   Enable paged mode
> w   Enable logging
> 
> Main menu, select an option:  [1-99 or e/p/w or 0 to quit] 13
> 
> SATA Maximum Queue Depth:  [0 to 255, default is 128]
> SAS Max Queue Depth, Narrow:  [0 to 65535, default is 256]
> SAS Max Queue Depth, Wide:  [0 to 65535, default is 256]
> Device Missing Report Delay:  [0 to 2047, default is 0]
> Device Missing I/O Delay:  [0 to 255, default is 0]
> 
> PhyNum  Link      MinRate  MaxRate  Initiator  Target    Port
> 0    Enabled     3.0      12.0    Disabled   Enabled     1
> 1    Enabled     3.0      12.0    Disabled   Enabled     1
> 2    Enabled     3.0      12.0    Disabled   Enabled     1
> 3    Enabled     3.0      12.0    Disabled   Enabled     1
> 4    Enabled     3.0      12.0    Enabled    Disabled  Auto
> 5    Enabled     3.0      12.0    Enabled    Disabled  Auto
> 6    Enabled     3.0      12.0    Enabled    Disabled  Auto
> 7    Enabled     3.0      12.0    Enabled    Disabled  Auto
> 