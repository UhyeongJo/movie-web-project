package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoxOfficeRepository extends JpaRepository<BoxOffice,String> {

    @Query("select distinct b from BoxOffice b " +
            "where  b.ranking is not null order by b.ranking asc  ")
    //fetch join 두개 존나위험해서 그냥 BatchSize사용함
    public List<BoxOffice> getBoxOfficeList();
    //left 없으면 null

    @Query("select distinct b from BoxOffice b left join fetch b.stillImage " +
            " where b.title LIKE %:title%")
    public BoxOffice getSerachMovie(@Param("title") String title);      //자세히보기

    @Query("select distinct b from BoxOffice b left join fetch b.stillImage" +
            " where b.title = :title")
    public BoxOffice getMovieRead(@Param("title")String title);

    @Query("select distinct b from BoxOffice b where b.title like %:title%")
    public List<BoxOffice> getMovieList(@Param("title")String title);


    @Query("select distinct b from BoxOffice b join fetch b.likeGoods"+
            " where b.ranking is null order by b.likeGoods.size desc")
    public List<BoxOffice> getLikeMovieList(Pageable pageable);
}
