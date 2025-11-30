
#!/bin/bash
rm -rf ./target

# Step 1: Prepare the release (updates the version and creates a tag)
mvn release:prepare
# Step 2: Perform the release (builds and deploys the release)
mvn release:perform
