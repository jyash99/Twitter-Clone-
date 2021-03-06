/**
 * This class is generated by jOOQ
 */
package tech.codingclub.helix.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Followers extends org.jooq.impl.TableImpl<tech.codingclub.helix.tables.records.FollowersRecord> {

	private static final long serialVersionUID = 255858467;

	/**
	 * The singleton instance of <code>public.followers</code>
	 */
	public static final tech.codingclub.helix.tables.Followers FOLLOWERS = new tech.codingclub.helix.tables.Followers();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<tech.codingclub.helix.tables.records.FollowersRecord> getRecordType() {
		return tech.codingclub.helix.tables.records.FollowersRecord.class;
	}

	/**
	 * The column <code>public.followers.user_id</code>.
	 */
	public final org.jooq.TableField<tech.codingclub.helix.tables.records.FollowersRecord, java.lang.Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>public.followers.following_id</code>.
	 */
	public final org.jooq.TableField<tech.codingclub.helix.tables.records.FollowersRecord, java.lang.Long> FOLLOWING_ID = createField("following_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * Create a <code>public.followers</code> table reference
	 */
	public Followers() {
		this("followers", null);
	}

	/**
	 * Create an aliased <code>public.followers</code> table reference
	 */
	public Followers(java.lang.String alias) {
		this(alias, tech.codingclub.helix.tables.Followers.FOLLOWERS);
	}

	private Followers(java.lang.String alias, org.jooq.Table<tech.codingclub.helix.tables.records.FollowersRecord> aliased) {
		this(alias, aliased, null);
	}

	private Followers(java.lang.String alias, org.jooq.Table<tech.codingclub.helix.tables.records.FollowersRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, tech.codingclub.helix.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<tech.codingclub.helix.tables.records.FollowersRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<tech.codingclub.helix.tables.records.FollowersRecord>>asList(tech.codingclub.helix.Keys.FOLLOWERS_USER_ID_FOLLOWING_ID_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<tech.codingclub.helix.tables.records.FollowersRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<tech.codingclub.helix.tables.records.FollowersRecord, ?>>asList(tech.codingclub.helix.Keys.FOLLOWERS__FOLLOWERS_USER_ID_FKEY, tech.codingclub.helix.Keys.FOLLOWERS__FOLLOWERS_FOLLOWING_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public tech.codingclub.helix.tables.Followers as(java.lang.String alias) {
		return new tech.codingclub.helix.tables.Followers(alias, this);
	}

	/**
	 * Rename this table
	 */
	public tech.codingclub.helix.tables.Followers rename(java.lang.String name) {
		return new tech.codingclub.helix.tables.Followers(name, null);
	}
}
