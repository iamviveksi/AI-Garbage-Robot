1. Wykonać komendy
```bash
sudo apt-get install g++ make cmake-data cmake-dbg python python-pip python-dev build-essential libfann-dev libfann2
sudo pip install --upgrade pip 
sudo pip install --upgrade virtualenv
sudo pip install fann2
```

2. http://sourceforge.net/projects/fann/files/fann/2.2.0/FANN-2.2.0-Source.zip/download
3. Rozpakować wejść do katalogu FANN-2.2.0-Source
4. Następnie

```bash
cmake .
sudo make install
cmake -D CMAKE_INSTALL_PREFIX:PATH=/usr .
sudo ldconfig
```
