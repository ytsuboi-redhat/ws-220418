package com.redhat.jp.labs.sample.backend.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

public class ProductBacklogItem {

    @Id
    private Long itemId;

    @NotNull
    @Size(min = 1)
    private String name;

    private String description;

    private String storyPoint;

    private String memo;

    public ProductBacklogItem() {
    }

    public ProductBacklogItem(String name, String description, String storyPoint, String memo) {
        this.name = name;
        this.description = description;
        this.storyPoint = storyPoint;
        this.memo = memo;
    }

    public ProductBacklogItem(long itemId, String name, String description, String storyPoint, String memo) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.storyPoint = storyPoint;
        this.memo = memo;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoryPoint() {
        return storyPoint;
    }

    public void setStoryPoint(String storyPoint) {
        this.storyPoint = storyPoint;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductBacklogItem other = (ProductBacklogItem) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        if (memo == null) {
            if (other.memo != null)
                return false;
        } else if (!memo.equals(other.memo))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (storyPoint == null) {
            if (other.storyPoint != null)
                return false;
        } else if (!storyPoint.equals(other.storyPoint))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        result = prime * result + ((memo == null) ? 0 : memo.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((storyPoint == null) ? 0 : storyPoint.hashCode());
        return result;
    }
}