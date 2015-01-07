# midje-notifier

Libnotify and OS X Notification output for [Midje][midje]

[midje]: http://github.com/marick/midje

## Installation

Add the following to your `:dev` `:dependencies` in `project.clj`:

[![Clojars Project](http://clojars.org/midje-notifier/latest-version.svg)](http://clojars.org/midje-notifier)

In the REPL, prior to calling `midje.repl/autotest`, run the following

```
user=> (use 'midje.notifier)
user=> (notify-me)
```

You can then work using the standard Midje workflow.

Midje-notifier currently requires that you have either the `notify-send` binary
on Linux or `terminal-notifier` on OS X somewhere in your PATH. The former comes
standard with libnotify, and the latter can be installed [here][tn].

[tn]: https://github.com/alloy/terminal-notifier

## License

Copyright © 2015 Griffin Smith

Distributed under the MIT License
