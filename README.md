# midje-notifier

Growl and libnotify output for [Midje][midje]

[midje]: http://github.com/marick/midje

## Installation

Add the following to your `:dev` `:dependencies` in `project.clj`:

[![Clojars Project](http://clojars.org/midje-notifier/latest-version.svg)](http://clojars.org/midje-notifier)

In the REPL, prior to calling `midje.repl/autotest`, run the following

```
user=> (use 'midje.notifier)
user=> (notifyme)
```

Midje-notifier currently requires that you have the `notify-send` binary
installed on your system, which comes standard with libnotify. It also only
works on Linux, because that's what I'm running. OS X support is coming soon,
though! (as soon as I can get my hands on a Mac to test with).

## License

Copyright © 2015 Griffin Smith

Distributed under the MIT License
