package eu.linksmart.api.event.datafusion;

/**
 * Created by angel on 18/11/15.
 */
public interface DataFusionWrapperAdvanced extends DataFusionWrapper {

    public void insertObject(String name,Object variable) throws UnsupportedOperationException;
    /**
     * Add additional package used in the engine
     * @param canonicalNameClassOrPkg String representing a class or a package path containing classes (e.g. my.package.*).
     *
     * @return <code>true</code> if the Class or Package Path was loaded into the CEP engine. <code>false</code> otherwise.
     * */
    public boolean loadAdditionalPackages( String canonicalNameClassOrPkg) throws Exception;

}
