# ht-frontmatter-datagen
Code in this repo is intended to create a CSV file on a per-page basis from a directory full of HathiTrust extracted features (EF) JSON files.  Currently, the code defines ~15 variables that are generated per page--variables that were designed to help with the training a classifier to identify portions of "factual" front matter in HT volumes.

Because the code is intended to support further analysis in a kit such as ``R``, the only defined ``main`` method also requires an argument naming a grouth truth file.  Two sample ground truth files are supplied in the ``data`` directory of the repo.

