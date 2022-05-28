package derevo;

import com.brunomnsilva.smartgraph.graphview.SmartLabelSource;
import derevo.Uzel;
import derevo.Color;
import java.util.ArrayList;

public class derevo2<T extends Comparable<T>> {

    private Uzel<T> mR;

    public derevo2() {
        mR = null;
    }

    private Uzel<T> parOf(Uzel<T> uzel) {
        return uzel != null ? uzel.par : null;
    }

    private boolean colOf(Uzel<T> uzel) {
        return uzel != null ? uzel.col : Color.RED.getValue();
    }

    private boolean isR(Uzel<T> uzel) {
        return ((uzel != null) && (uzel.col == Color.RED.getValue())) ? true : false;
    }

    private boolean isB(Uzel<T> uzel) {
        return !isR(uzel);
    }

    private void setB(Uzel<T> uzel) {
        if (uzel != null) uzel.col = Color.BLACK.getValue();
    }

    private void setR(Uzel<T> uzel) {
        if (uzel != null) uzel.col = Color.RED.getValue();
    }

    private void setPar(Uzel<T> uzel, Uzel<T> par) {
        if (uzel != null) uzel.par = par;
    }


    private void setCol(Uzel<T> uzel, boolean col) {
        if (uzel != null) uzel.col = col;
    }

    private ArrayList<T> preOrder(Uzel<T> tree) {
        ArrayList<T> s = new ArrayList<>();
        if (tree != null) {
            s.add(tree.key);
            s.addAll(preOrder(tree.lef));
            s.addAll(preOrder(tree.ri));
        }
        return s;
    }

    public ArrayList<T> preOrder() {
        return preOrder(mR);
    }

    private ArrayList<T> inOrder(Uzel<T> tree) {
        ArrayList<T> s = new ArrayList<>();
        if (tree != null) {
            s.addAll(inOrder(tree.lef));
            s.add(tree.key);
            s.addAll(inOrder(tree.ri));
        }
        return s;
    }

    public ArrayList<T> inOrder() {
        return inOrder(mR);
    }

    private ArrayList<T> postOrder(Uzel<T> tree) {
        ArrayList<T> s = new ArrayList<>();
        if (tree != null) {
            s.addAll(postOrder(tree.lef));
            s.addAll(postOrder(tree.ri));
            s.add(tree.key);
        }
        return s;
    }

    public ArrayList<T> postOrder() {
       return postOrder(mR);
    }

    private Uzel<T> search(Uzel<T> x, T key) {
        if (x == null) return x;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) return search(x.lef, key);
        else if (cmp > 0) return search(x.ri, key);
        else return x;
    }

    public Uzel<T> search(T key) {
        return search(mR, key);
    }

    private Uzel<T> iterativeSearch(Uzel<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0) x = x.lef;
            else if (cmp > 0) x = x.ri;
            else return x;
        }

        return x;
    }

    public Uzel<T> iterativeSearch(T key) {
        return iterativeSearch(mR, key);
    }


    private Uzel<T> max(Uzel<T> tree) {
        if (tree == null) return null;

        while (tree.ri != null) tree = tree.ri;
        return tree;
    }

    public T max() {
        Uzel<T> p = max(mR);
        if (p != null) return p.key;

        return null;
    }

    private Uzel<T> min(Uzel<T> tree) {
        if (tree == null) return null;

        while (tree.lef != null) tree = tree.lef;
        return tree;
    }

    public T min() {
        Uzel<T> p = min(mR);
        if (p != null) return p.key;

        return null;
    }


    public Uzel<T> successor(Uzel<T> x) {
        if (x.ri != null) return min(x.ri);

        Uzel<T> y = x.par;
        while ((y != null) && (x == y.ri)) {
            x = y;
            y = y.par;
        }

        return y;
    }

    public Uzel<T> predecessor(Uzel<T> x) {
        return x.getParent();
    }

    private void riRotate(Uzel<T> y) {
        Uzel<T> x = y.lef;

        y.lef = x.ri;
        if (x.ri != null) x.ri.par = y;

        x.par = y.par;

        if (y.par == null) {
            this.mR = x;
        } else {
            if (y == y.par.ri) y.par.ri = x;
            else y.par.lef = x;
        }

        x.ri = y;
        y.par = x;
    }

    private void lefRotate(Uzel<T> x) {
        Uzel<T> y = x.ri;

        x.ri = y.lef;
        if (y.lef != null) y.lef.par = x;

        y.par = x.par;

        if (x.par == null) {
            this.mR = y;
        } else {
            if (x.par.lef == x) x.par.lef = y;
            else x.par.ri = y;
        }

        y.lef = x;
        x.par = y;
    }

    private void insertFixUp(Uzel<T> uzel) {
        Uzel<T> par, gpar;

        while (((par = parOf(uzel)) != null) && isR(par)) {
            gpar = parOf(par);

            if (par == gpar.lef) {
                Uzel<T> dada = gpar.ri;
                if ((dada != null) && isR(dada)) {
                    setB(dada);
                    setB(par);
                    setR(gpar);
                    uzel = gpar;
                    continue;
                }

                if (par.ri == uzel) {
                    Uzel<T> tmp;
                    lefRotate(par);
                    tmp = par;
                    par = uzel;
                    uzel = tmp;
                }

                setB(par);
                setR(gpar);
                riRotate(gpar);
            } else {
                Uzel<T> dada = gpar.lef;
                if ((dada != null) && isR(dada)) {
                    setB(dada);
                    setB(par);
                    setR(gpar);
                    uzel = gpar;
                    continue;
                }

                if (par.lef == uzel) {
                    Uzel<T> tmp;
                    riRotate(par);
                    tmp = par;
                    par = uzel;
                    uzel = tmp;
                }

                setB(par);
                setR(gpar);
                lefRotate(gpar);
            }
        }

        setB(this.mR);
    }

    private void insert(Uzel<T> uzel) {
        int cmp;
        Uzel<T> y = null;
        Uzel<T> x = this.mR;

        while (x != null) {
            y = x;
            cmp = uzel.key.compareTo(x.key);
            if (cmp < 0) x = x.lef;
            else x = x.ri;
        }

        uzel.par = y;
        if (y != null) {
            cmp = uzel.key.compareTo(y.key);
            if (cmp < 0) y.lef = uzel;
            else y.ri = uzel;
        } else {
            this.mR = uzel;
        }

        uzel.col = Color.RED.getValue();

        insertFixUp(uzel);
    }

    public void insert(T key) {
        Uzel<T> uzel = new Uzel<T>(key, Color.BLACK.getValue(), null, null, null);

        if (uzel != null) {
            insert(uzel);
        }
    }

    private void removeFixUp(Uzel<T> uzel, Uzel<T> par) {
        Uzel<T> other;

        while ((uzel == null || isB(uzel)) && (uzel != this.mR)) {
            if (par.lef == uzel) {
                other = par.ri;
                if (isR(other)) {
                    setB(other);
                    setR(par);
                    lefRotate(par);
                    other = par.ri;
                }

                if ((other.lef == null || isB(other.lef)) && (other.ri == null || isB(other.ri))) {
                    setR(other);
                    uzel = par;
                    par = parOf(uzel);
                } else {

                    if (other.ri == null || isB(other.ri)) {
                        setB(other.lef);
                        setR(other);
                        riRotate(other);
                        other = par.ri;
                    }
                    setCol(other, colOf(par));
                    setB(par);
                    setB(other.ri);
                    lefRotate(par);
                    uzel = this.mR;
                    break;
                }
            } else {

                other = par.lef;
                if (isR(other)) {
                    setB(other);
                    setR(par);
                    riRotate(par);
                    other = par.lef;
                }

                if ((other.lef == null || isB(other.lef)) && (other.ri == null || isB(other.ri))) {

                    setR(other);
                    uzel = par;
                    par = parOf(uzel);
                } else {

                    if (other.lef == null || isB(other.lef)) {
                        setB(other.ri);
                        setR(other);
                        lefRotate(other);
                        other = par.lef;
                    }

                    setCol(other, colOf(par));
                    setB(par);
                    setB(other.lef);
                    riRotate(par);
                    uzel = this.mR;
                    break;
                }
            }
        }

        if (uzel != null) setB(uzel);
    }

    private void remove(Uzel<T> uzel) {
        Uzel<T> chil, par;
        boolean col;
        if ((uzel.lef != null) && (uzel.ri != null)) {
            Uzel<T> per = uzel;

            per = per.ri;
            while (per.lef != null) per = per.lef;

            if (parOf(uzel) != null) {
                if (parOf(uzel).lef == uzel) parOf(uzel).lef = per;
                else parOf(uzel).ri = per;
            } else {
                this.mR = per;
            }

            chil = per.ri;
            par = parOf(per);
            col = colOf(per);

            if (par == uzel) {
                par = per;
            } else {
                if (chil != null) setPar(chil, par);
                par.lef = chil;

                per.ri = uzel.ri;
                setPar(uzel.ri, per);
            }

            per.par = uzel.par;
            per.col = uzel.col;
            per.lef = uzel.lef;
            uzel.lef.par = per;

            if (col == Color.BLACK.getValue()) removeFixUp(chil, par);

            uzel = null;
            return;
        }

        if (uzel.lef != null) {
            chil = uzel.lef;
        } else {
            chil = uzel.ri;
        }

        par = uzel.par;
        col = uzel.col;

        if (chil != null) chil.par = par;

        if (par != null) {
            if (par.lef == uzel) par.lef = chil;
            else par.ri = chil;
        } else {
            this.mR = chil;
        }

        if (col == Color.BLACK.getValue()) removeFixUp(chil, par);
        uzel = null;
    }

    public void remove(T key) {
        Uzel<T> uzel;

        if ((uzel = search(mR, key)) != null) remove(uzel);
    }

    private void destroy(Uzel<T> tree) {
        if (tree == null) return;

        if (tree.lef != null) destroy(tree.lef);
        if (tree.ri != null) destroy(tree.ri);

        tree = null;
    }

    public void clear() {
        destroy(mR);
        mR = null;
    }

    private void print(Uzel<T> tree, T key, int direction) {

        if (tree != null) {

            if (direction == 0) {
                System.out.printf("%2d(B) корень\n", tree.key);
                // t=500;
                //s=500;}
            } else {
                System.out.printf("%2d  %6s потомок %2d(%s)\n", tree.key, isR(tree) ? "R" : "B", key, direction == 1 ? "правый" : "левый");
            }
            print(tree.lef, tree.key, -1);
            print(tree.ri, tree.key, 1);
        }
    }

    public void print() {
        if (mR != null) print(mR, mR.key, 0);
    }

}

