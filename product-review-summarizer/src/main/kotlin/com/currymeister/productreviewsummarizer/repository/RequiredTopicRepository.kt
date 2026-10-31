package com.currymeister.productreviewsummarizer.repository

import com.currymeister.productreviewsummarizer.entity.RequiredTopic
import org.springframework.data.jpa.repository.JpaRepository

interface RequiredTopicRepository : JpaRepository<RequiredTopic, Int>
