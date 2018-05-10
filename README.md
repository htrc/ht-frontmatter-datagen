# ht-frontmatter-datagen
Code in this repo is intended to create a CSV file on a per-page basis from a directory full of HathiTrust extracted features (EF) JSON files.  Currently, the code defines ~15 variables that are generated per page--variables that were designed to help with the training a classifier to identify portions of "factual" front matter in HT volumes.

Because the code is intended to support further analysis in a kit such as ``R``, the only defined ``main`` method also requires an argument naming a grouth truth file.  Two sample ground truth files are supplied in the ``data`` directory of the repo.

After checkout, type the following to compile:
```
./build.sh
```
This requires ``maven`` to be installed.


## Sample Usage
To run the code, file, ``./data/files.txt`` that enumerates the path to a bunch of HT EF JSON files--one per volume under analysis.  We also assume that we have a ground truth file (one volumeid-pageid/label pair per line) in ``./data/ground-truth.txt``.  We can now issue this command:
```
./run.sh mains.ExtractedFeatures2CSV ./data/files.txt ./data/ground-truth.txt > page-data.csv
```
which will create ``page-data.csv``, a file with one line per page, whose columns are comprised of predictor variables.
