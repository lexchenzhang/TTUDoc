For questions 5-9, go to your kernel source directory and do either a `make menuconfig` or a `make xconfig` (you may need to install packages (https://askubuntu.com/questions/520864/how-to-install-needed-qt-packages-to-build-kernel-on-14-04) to use xconfig). This allows you to view and configure the options that your kernel will be built with. 

1. What is the version of the kernel you compiled?
5.9.1
2. Run the following command from a terminal: ``uname -a``.
What is the output from your old kernel? What's the output from the new kernel?

old kernel:
Linux lex-Ubuntu 5.4.0-48-generic #52-Ubuntu SMP Thu Sep 10 10:58:49 UTC 2020 x86_64 x86_64 x86_64 GNU/Linux

new kernel:
Linux lex-Ubuntu 5.9.1 #1 SMP Sun Oct 25 16:11:17 EDT 2020 x86_64 x86_64 x86_64 GNU/Linux

3. Go to the `/boot` directory and issue an `ls -rlt` command (this puts newly created files at the bottom). You should see four new files: vmlinuz-*version*, System.map-*version*, config-*version* and initrd.img-*version*, where *version* is the kernel version you compiled. Give a short description of the purpose of each of these four files.
-vmlinuz-version is a compressed Linux kernel, and it's exeutable.
-the system.map file is mainly used to debug kernel crashes and a new system.map is generated with each kernel compile.
-config-version file is the place where installed kernel configuration.
-initrd.img-version file provides the capability to load a RAM disk by the boot loader.
(some of the reference are from wiki)

4. What is the size of the kernel binary (e.g., your vmlinuz file) in MB? You can use the ``ls -lh`` command to determine this.
it's about 12MB

5. Find the "Timer frequency" configuration option (under "Processor type and features"). What does this configuration option control?  What performance differences do you expect to observe with the different options?
-this configuration option is for changing the interrupt frequency, this setting can impact on overall system latency and throughput in certain scenarios.
-people will generally choose a high value(like 1000Hz) when they looking for latency and a low value(like 100Hz) when they prefer throughput.

6. What does the configuration menu say about a 100Hz timer interrupt vs a 1000Hz timer interrupt?
it says:
allows the configuration of the timer frequency. It is customary to have the timer interrupt run at 1000 Hz but 100 Hz may be more beneficial for servers and NUMA systems that do not need to have a fast response for user interaction and that may experience bus contention and cacheline bounces as a result of timer interrupts.

7. Look for the "Network packet filtering framework (Netfilter)" option and describe what Netfilter is. You may use Netfilter later on (e.g., can be an option for the final project).
-Netfilter is a framework provided by the Linux kernel that allows various networking-related operations to be implemented in the form of customized handlers.Netfilter offers various functions and operations for packet filtering, network address translation, and port translation, which provide the functionality required for directing packets through a network and prohibiting packets from reaching sensitive locations within a network.
link:https://en.wikipedia.org/wiki/Netfilter

8. Look for the "Randomize the address of the kernel image (KASLR)" option. What does this option do?
-Kernel address space layout randomization (KASLR) enables address space randomization for the Linux kernel image by randomizing where the kernel code is placed at boot time.
link:https://en.wikipedia.org/wiki/Address_space_layout_randomization

9. Ensure that you have the "64-bit kernel" option enabled and look under the "Security options" category for the "Remove the kernel mapping in user mode" option. What does the help message say about this option?
-CONFIG_64BIT"
say yes to build a 64-bit kernel - formerly known as x86_64
say no to build a 32-bit kernel - formerly known as i386

symbol: 64BIT [=y]
type: bool
defined at arch/x86/kconfig:3
prompt: 64-bit kernel
visible if: x86=x86

10. Is the kernel compiled against a C library (like regular user-space programs)? Why or why not?
-this answer if from Stephen Kitt
No, the kernel doesn’t depend on the standard C library (or any other library), it is self-contained. User-space programs don’t necessarily depend on the C library either. The C library provides convenient wrappers for system calls, but they can be called directly without going through the C library, and other language runtimes can provide their own wrappers.
link:https://unix.stackexchange.com/questions/523358/does-the-kernel-itself-depend-on-the-standard-c-libraries
**To answer these questions, you can definitely refer to things online, but don't forget to provide links.**
