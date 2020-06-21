##
# Project Title
#
# @file
# @version 0.1

build:
	git submodule update --recursive --init
	cd modules/spire && make jar
	clojure -A:native-image

# end
