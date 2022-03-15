alter table proxy
    add CONSTRAINT name_unique unique (name);

alter table proxy
    add CONSTRAINT hostname_unique unique (hostname);

create index proxy_name_type_idx ON proxy (name, type);